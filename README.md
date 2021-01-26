# HtmlParser
Html解析器封装，加载图片时可以根据 TextView 的高度自动调整图片的大小。

1.在项目根 build.gradle 文件中添加 Maven 仓库：
```
allprojects {
    repositories {
        maven {
            url "http://maven.ituns.org/repository/maven-public/"
        }
    }
}
```

2.在模块 build.gradle 中添加引用：
```
dependencies {
    implementation "org.ituns.android:html:1.0.0"
}
```