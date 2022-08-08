# CraftEconomy
Ekonomický plugin na správu server ekonomiky, vault ekonomiky a základních statistik hráčů.

### Integrace

```
maven {
    url "https://gitlab.com/api/v4/groups/craftmania/-/packages/maven"
    name "GitLab"
    credentials(HttpHeaderCredentials) {
        name = 'Private-Token'
        value = GITLAB_TOKEN
    }
    authentication {
        header(HttpHeaderAuthentication)
    }
}

dependencies {
    compileOnly group: 'cz.craftmania.crafteconomy', name:'crafteconomy', version: '2.1.0'
}
```
