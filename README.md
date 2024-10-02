# lifestyle-ph-server



## Getting started

1. Import project to the IDE (recommended IntelliJ), use java 17 sdk
2. Copy a file from **.development-settings** called `application-prod.properties` to the resources folder of **web-core** module.
3. Edit the **application-prod.properties** to set the keys and ids (ask dev for the credentials and values).
4. Build first the project with `mvn clean install -DskipTests` to skip test or execute `mvn clean install` without skipping test.
5. Then Go to the Intellij's Menu `Run >> Edit Configuration` 
6. Add a new **Application** and check the `store as project file` to set the path to **.development-settings** for the run configuration (the xml configuration is for IntelliJ only, ask dev for the other IDE setup).
7. Update the jasypt property `-Djasypt.encryptor.password` for the actual password.
8. Generate keys using the test class in *WebCoreApplicationTests* (`generateKeys` and `generateSecretKey` test case).
9. Get the *Base64Encode* of the keys on the logs of unit test execution to be set on the client side.
10. Run using the Configuration.

## To Encrypt Properties
1. Go to the web-core path
2. Execute `mvn jasypt:encrypt-value -Djasypt.encryptor.password=<jasypt-password> -Djasypt.plugin.value=<value-to-be-encrypted>`.
3. Save the encrypted value on the properties with **ENC(<encrypted-value>)**.

## To Encrypt Properties via RSA that will be use in the client
1. Go to the *WebCoreApplicationTests*.
2. Change the parameter in the `encryptViaRSA` method from `testEncryptViaRSA` test case to the key or value need to be encrypted.
3. Execute testEncryptViaRSA and get the encrypted value on the logs.
4. Save the value on the client side .env file.

## To Encrypt Properties via RSA that will be use in the client
1. To run unit test you need to add the VM option from the `LifeStyle Runner` for the java module.