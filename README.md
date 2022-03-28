### Install the Validator in the Mule Runtime

The corresponding JAR should be added under the lib folder within the mule-agent plugin, which is contained in the plugins folder of the Mule instance.

For example, $MULE_HOME/server-plugins/mule-agent-plugin/lib/mule-agent-file-size-validator.jar.

### Mule Agent File Size Validator Configuration

In the following configuration, we are going to restrict the size of the application to 25 MB.

File: $MULE_HOME/conf/mule-agent.yml

```
services:
  mule.agent.artifact.validator.service:
    enabled: true
    validators:
    - type: fileSizeValidator
      name: defaultFileSizeValidator
      enabled: true
      args:
        # Maximum file size in Megabytes supported by this Mule runtime 
        limit: 25
```

### Log4j Configuration

File: $MULE_HOME/conf/log4j2.xml

```
 <AsyncLogger name="com.mycompany.agent" level="INFO"/>
```

### Test the File Size Validator

Deploy an application that weighs more than 25 MB

Command

```
curl -X PUT 'http://localhost:9999/mule/applications/app-01' \
-H 'Content-Type: application/octet-stream' \
-F 'file=@"/repositories/apps/app-45MB.jar"'
```

Response

```
{
  "type": "class com.mycompany.agent.exceptions.FileSizeLimitExceededException",
  "message": "File size exceeds the maximum limit '25 MB' - Application: 'app-01' weighs '45 MB'."
}
```

Logs

```
INFO  2022-03-28 14:35:03,845 [qtp230713563-64] [processor: ; event: ] com.mulesoft.agent.services.application.AgentApplicationService: Deploying the app-01 application from jar file.
INFO  2022-03-28 14:35:03,933 [qtp230713563-64] [processor: ; event: ] com.mycompany.agent.MuleAgentFileSizeValidator: File path: /tmp/mule-received-artifact-8053379609096736933/app-01.jar
INFO  2022-03-28 14:35:03,933 [qtp230713563-64] [processor: ; event: ] com.mycompany.agent.MuleAgentFileSizeValidator: File size (MB): 45
ERROR 2022-03-28 14:35:03,933 [qtp230713563-64] [processor: ; event: ] com.mycompany.agent.MuleAgentFileSizeValidator: File size exceeds the maximum limit '25 MB' - Application: 'app-01' weighs '45 MB'.
ERROR 2022-03-28 14:35:03,940 [qtp230713563-64] [processor: ; event: ] com.mulesoft.agent.external.handlers.deployment.ApplicationsRequestHandler: Error performing the deployment of app-01. Cause: FileSizeLimitExceededException: File size exceeds the maximum limit '25 MB' - Application: 'app-01' weighs '45 MB'.
```