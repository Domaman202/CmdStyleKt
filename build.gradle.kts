plugins {
    kotlin("jvm") version "2.2.21"
    id("com.gradleup.nmcp.aggregation").version("1.2.1")
    `maven-publish`
    `java-library`
    signing
}

group = "io.github.domaman202"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

java {
    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(8)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("CommandLine Style Library")
                description.set("Library for text formatting within the console")
                url.set("https://github.com/Domaman202/CmdStyleKt")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("DomamaN202")
                        name.set("DomamaN202")
                        email.set("vip.domaman@mail.ru")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Domaman202/CmdStyleKt.git")
                    developerConnection.set("scm:git:ssh://github.com/Domaman202/CmdStyleKt.git")
                    url.set("https://github.com/Domaman202/CmdStyleKt")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}

nmcpAggregation {
    centralPortal {
        username = System.getenv("MAVEN_USERNAME")
        password = System.getenv("MAVEN_PASSWORD")
        publicationName = "$group:$name:$version"
        publishingType = "USER_MANAGED"
        publishingType = "AUTOMATIC"
    }

    publishAllProjectsProbablyBreakingProjectIsolation()
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}