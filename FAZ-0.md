# Faz 0 — Mülakat Soruları ve Cevapları

> Kargo Platformu / Spring Boot yol haritası
> Kapsam: Maven multi-module, Spring core, auto-configuration, bean lifecycle

---

## 1. `dependencyManagement` ile `dependencies` arasındaki fark nedir?

`dependencyManagement` bir **versiyon kataloğudur**. Hiçbir bağımlılık eklemez, sadece
"biri bu artifact'i isterse şu versiyonu, şu scope'u alsın" der. `dependencies` ise
gerçekten ekler ve classpath'e girer.

Bu ayrım sayesinde alt modüller versiyon yazmadan sadece `groupId` + `artifactId`
yazar; versiyon tek yerden yönetilir.

**Kanıt (yaşadığım):** Kök pom'un `dependencyManagement`'ında `kargo-domain` tanımlıydı
ama `kargo-infrastructure`'ın classpath'ine ancak onun `<dependencies>` bloğuna
yazdığımda girdi.

---

## 2. Parent pom bir bağımlılık mıdır?

Hayır. Parent bir **kalıtım** ilişkisidir; property'ler, plugin ayarları ve
`dependencyManagement` aşağı akar. Bağımlılık değildir, classpath'e hiçbir şey girmez.

`kargo-domain`, `kargo-platform`'dan tek satır kod kullanmaz — ama ondan `java.version`
property'sini miras alır.

**Kanıt:** `mvn -pl kargo-domain help:evaluate -Dexpression=maven.compiler.release`
komutu `21` döndü; oysa alt modülde hiçbir Java ayarı yok. Zincir üç katman:
`spring-boot-starter-parent` → `kargo-platform` → `kargo-domain`.

---

## 3. `packaging=pom` ne demek, ne zaman kullanılır?

Bu projeden derlenmiş bir çıktı üretilmez; üretilen tek artifact `pom.xml` dosyasının
kendisidir. Proje aggregator (modülleri toplar) ve parent (ayar dağıtır) rolü oynar.

**Nasıl anlarsın:** `<modules>` varsa veya `src` klasörü yoksa → `pom`.

**Yaşadığım hata:** `<packaging>` yazmayı unutunca varsayılan `jar` oldu, Maven kod
içermeyen projeyi jar'a paketlemeye çalıştı. Çıktıdaki `[ jar ]` / `[ pom ]` satırından
anlaşılıyor.

---

## 4. Maven reactor build sırasını kim belirler?

`<modules>` etiketindeki sıra **değil**. Maven bağımlılık grafiğinden kendi çıkarır.

**Kanıt:** Alt pom'larda `<parent>` bloğu yokken parent 4. sıradaydı (yani çocuklar onu
tanımıyordu). `<parent>` eklediğim an 1. sıraya çıktı — Maven artık "önce parent, sonra
çocuk" zorunluluğunu görüyordu.

---

## 5. `-am` bayrağı ne yapar? Neden `spring-boot:run` ile kullanılmaz?

`-am` (also make) reactor'a, hedef modülün bağımlı olduğu diğer modülleri de dahil eder.

- Derlerken gerekli: `mvn -pl kargo-api -am clean install -DskipTests`
- Çalıştırırken gereksiz ve zararlı: `mvn -pl kargo-api spring-boot:run`

Çünkü `-am` reactor'a birden fazla modül sokar, Maven her biri için `run` goal'ünü
çalıştırmaya kalkar.

**İlgili bilgi:** `mvn compile` sırasında `kargo-domain` yerel repository'de (`~/.m2`)
olmadığı halde build geçti — Maven aynı reactor içindeki modülün `target/classes`
klasörünü doğrudan classpath'e ekler.

---

## 6. `@SpringBootApplication` hangi anotasyonları birleştirir?

1. `@Configuration`
2. `@EnableAutoConfiguration`
3. `@ComponentScan`

`@ComponentScan` **sadece kendi paketinden aşağı** tarar. Bu yüzden ana sınıfın paketi,
diğer tüm paketlerin atası olmalıdır.

---

## 7. Auto-configuration nasıl karar verir?

`@Conditional` anotasyonlarıyla. Üç şeye bakar: classpath'te ne var, hangi property
tanımlı, kullanıcı zaten bu tipte bir bean tanımlamış mı.

`@ConditionalOnMissingBean` = "kullanıcı bu tipte bean tanımlamamışsa benimkini koy".
Yani **senin tanımladığın her zaman kazanır**, Spring sadece boşlukları doldurur.

`/actuator/conditions` her kararın gerekçesini gösterir:
- `positiveMatches` → devreye girdi, sebebiyle birlikte
- `negativeMatches` → girmedi, hangi koşul sağlanmadı

---

## 8. `@ConfigurationProperties` vs `@Value`

| | `@ConfigurationProperties` | `@Value` |
|---|---|---|
| Kapsam | İlişkili ayar grubu (`kargo.pricing.*`) | Tek bir değer |
| Bağlama | Relaxed binding: `base-price` → `basePrice` | Tam ifade yazılır |
| Doğrulama | `@Validated` + `@NotNull` ile açılışta doğrulanır | Yok |
| Test | Düz nesne, `new PricingProperties(...)` | Spring'e bağımlı |

**Avantaj:** Eksik/hatalı ayar varsa uygulama hiç ayağa kalkmaz — hatayı prod'da ilk
istekte değil, deploy anında yakalarsın.

---

## 9. Profil dosyası `application.yml`'yi ezer mi?

Tam olarak "ezmez" — **üstüne yazar**. Spring önce `application.yml`'yi okur, sonra aktif
profil dosyasındaki anahtarları onun üzerine bindirir. Sadece **kesişen anahtarlar**
değişir; profil dosyasında olmayan ayarlar ana dosyadan gelmeye devam eder.

**Kanıt:** `application-prod.yml`'de sadece `base-price` vardı, `per-kg` yoktu. Prod
profilinde çalıştırınca `base-price` değişti ama `per-kg` `application.yml`'deki değeriyle
geldi.

---

## 10. Spring bean lifecycle sırası nedir?

```
1. Örnekle (instantiate)
2. Bağımlılıkları enjekte et
3. BeanPostProcessor.postProcessBeforeInitialization
4. @PostConstruct
5. BeanPostProcessor.postProcessAfterInitialization
6. Kullanıma hazır
```

**Kritik nokta:** `postProcessAfterInitialization`'ın döndürdüğü nesne orijinalin yerine
geçer. Spring `@Transactional` ve `@Cacheable` proxy'lerini tam burada devreye sokar.

Bu yüzden **aynı sınıf içinden çağrılan `@Transactional` metot çalışmaz** — proxy'yi
atlamış olursun. (Faz 3'te Proxy pattern'de detaylandırılacak.)

**Dikkat:** Lifecycle sırası her bean'in *kendi içinde* geçerlidir, bean'ler arasında
değil. İki farklı bean'in loglarını karşılaştırırsan sıra karışık görünür.

---

# Faz 0'da Aldığım Hatalar

| Hata | Belirti | Sebep |
|---|---|---|
| `<packaging>` yazılmadı | Çıktıda `[ jar ]` | Varsayılan `jar` |
| `com.cargo` yazım hatası | **Build SUCCESS verdi** | `dependencyManagement` sadece katalog, kimse istemediği için kontrol edilmedi |
| `<relativePath>pom.xml</relativePath>` | "Non-resolvable parent POM" | Yol çocuk klasöre göre çözülür, `../pom.xml` olmalı |
| `SpringApplication.run(String.class)` | "no ServletWebServerFactory bean" | Ana sınıf tanınmadı → `@SpringBootApplication` hiç okunmadı → auto-configuration çalışmadı |
| YAML girinti hatası (2 kez) | Ayar sessizce yok sayıldı | `spring.application.profiles.active` diye okundu, `spring.profiles.active` olmalıydı |
| PowerShell `-D` parametresi | "Unknown lifecycle phase .compiler.release" | Nokta içeren `-D` argümanları tırnak içine alınmalı |

## En değerli teşhis hikâyesi (STAR için)

**Durum:** Uygulama "no ServletWebServerFactory bean" hatasıyla açılmadı. Hata mesajı
eksik bağımlılığı işaret ediyordu.

**Eylem:** `mvn dependency:tree` ile Tomcat'in classpath'te olduğunu doğruladım — yani
mesaj yanıltıcıydı. `--debug` ile çalıştırınca Spring'in ana kaynak sınıf olarak
`java.lang.String` yüklediğini gördüm.

**Sonuç:** `SpringApplication.run(String.class, args)` yazmışım. Eksik olan bağımlılık
değil, o bağımlılığı bean'e çevirecek auto-configuration mekanizmasıydı.

**Ders:** `dependency:tree` bir jar'ın classpath'te olduğunu söyler, o jar'ın
*kullanıldığını* söylemez. Aradaki farkı auto-configuration kurar.