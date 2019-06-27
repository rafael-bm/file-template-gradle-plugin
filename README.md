# Simple File Templating
Replace placeholder from template files

# Usage scenario

Imagine you have a docker-compose.yaml file and you would like to update the images' versions everytime you commit in git.


_docker-compose-template.yaml_

```yaml
version: '3.2'
services:
  foo-service:
    image: foo:{{FOO_BAR}}
  bar-service:
    image: bar:{{BAR_VERSION}}
```

Given valueFile, eg.: 

_version.json_

```json
{
  "project": {
    "foo" : {
      "version" : "1.0.1"
    },
    "bar" : {
      "version" : "2.1.0"
    }
  }
}
```
With a simple task of this plugin, we can be able to archive the following result:

_docker-compose.yaml_

```yaml
version: '3.2'
services:
  foo-service:
    image: foo:1.0.1
  bar-service:
    image: bar:2.1.0
```

Of course a docker-compose file is a simple thing to change manually. but given a complex files, such as, terraform, 
cloudformation, kubenetes specs. this plugin can help to save some time.


#How to configure

for a gradle build with kotlin dsl 

Import the necessary classes required for plugin configuration
```kotlin
import uk.co.mulecode.filetemplate.plugin.FileTemplateTask
import uk.co.mulecode.filetemplate.plugin.model.TemplateConfig
import uk.co.mulecode.filetemplate.plugin.model.TemplateData
import uk.co.mulecode.filetemplate.plugin.model.TemplateDataItem
```

import the plugin
```kotlin
plugins {
    id("java")
    id("uk.co.mulecode.file-template") version "1.0.0"
}
```
```
Note! The version plugin might vary, Check the realese notes for new features or changes.

```
Configure a general configuration:
```kotlin
configure<TemplateConfig> {
    propertyFile = "version.json"
    configDetails = listOf(
            TemplateData(
                    "docker-compose-template.yaml",
                    "docker-compose.yaml",
                    listOf(
                            TemplateDataItem(
                                    "{{FOO_BAR}}",
                                    "project.foo.version"
                            ),
                            TemplateDataItem(
                                    "{{BAR_BAR}}",
                                    "project.bar.version"
                            )
                    )
            )
    )
}
```
Execute gradle task

```bash
gradle templateFile
```

You can also configure a custom task with a custom configuration:

```kotlin
tasks {
    register<FileTemplateTask>("templateForDev") {
        propertyFile = "version-dev.json"
    }
}
```

in this way you can execute multiple configuration according to your pipeline definitions.


Thank you
Rafael Mule