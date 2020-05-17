<br />
<p align="center">
      <h1 align="center">CraftEconomy</h1>
</p>
<p align="center">
  <a href="https://java.com/">
    <img src="http://ForTheBadge.com/images/badges/made-with-java.svg" alt="Made with Java">
    
  </a>
</p>
<p align="center">
    Plugin spravující ekonomiku na serverech - CraftCoins, CraftTokens atd. + Vault
</p

## Obsah

* [Integrace do pluginu](#integrace-crafteconomy-do-pluginu)

## Integrace CraftEconomy do pluginu

#### Gradle

```
repositories {
    maven { url "https://packages.craftmania.cz/repository/craftmania/" }
}

dependencies {
    compileOnly ('cz.craftmania.crafteconomy:crafteconomy:1.3.3') {
        exclude group: 'io.github.jorelali'
    }
}
```

#### Maven

```
<repository>
    <id>craftmania</id>
    <url>https://packages.craftmania.cz/repository/craftmania/</url>
</repository>

<dependency>
    <groupId>cz.craftmania.crafteconomy</groupId>
    <artifactId>crafteconomy</artifactId>
    <version>1.3.3</version>
</dependency>
```
