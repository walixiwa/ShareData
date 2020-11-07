# ShareData
使用Sqlite代替SharedPreference

##使用方法

```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```java
dependencies {
	        implementation 'com.github.walixiwa:ShareData:1.0.0'
	}
```

```java
Application下初始化
ShareData.init(this,version = 1)

ShareData.instance.put("testKey", "存入的值", "owner")

val value = ShareData.instance.get("testKey", "默认值", "owner")

ShareData.instance.get("owner").forEach {
    Log.e("value", it.toString())
}
```
