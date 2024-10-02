package com.lifestyle.ph.web;

import com.lifestyle.ph.common.utils.EncryptionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = WebCoreApplication.class)
@TestPropertySource(locations = {
        "classpath:application-test.properties",
})
@AutoConfigureMockMvc
class WebCoreApplicationTests {

    @Autowired
    private EncryptionUtil encryptionUtil;

    private String testText = "Ridiculus. Facilisi mus pulvinar. Aliquam ullamcorper maecenas bibendum laoreet lacu " +
            "nisl auctor adipiscing pede. Arcu faucibus tincidunt dignissim nonummy class ligula turpis lectus inter" +
            "mcongue ut mauris. Ac hymenaeos quisque justo. Magna ac vehicula Sit metus vivamus nam mattis volutpat," +
            "arcu. Elementum laoreet dignissim justo primis penatibus dictum, vestibulum auctor feugiat lorem Etiam " +
            "nisl netus dis interdum proin enim rhoncus nec. Senectus pharetra id nulla nostra nascetur id magna " +
            "sollicitudin varius ligula phasellus purus tempus. Dignissim ac cum accumsan, phasellus praesent " +
            "ullamcorper in cum cras leo conubia mi fringilla massa cras a mattis justo dui vehicula ante phasellus " +
            "etiam malesuada. Nostra tortor tristique semper rutrum, metus. Molestie mi gravida etiam lacus, morbi " +
            "lectus. Congue viverra sodales. Sapien justo. Fermentum lacinia. Accumsan viverra malesuada parturient " +
            "nostra, at amet mi tincidunt magnis, non praesent neque proin. Semper. Tincidunt hymenaeos pellentesque " +
            "montes parturient convallis.\n Euismod ipsum, sem nulla imperdiet nunc euismod pretium interdum cubilia " +
            "suscipit integer urna nec cursus vitae taciti, class aenean etiam fringilla conubia auctor elementum " +
            "scelerisque viverra tristique hac et sagittis fames lobortis laoreet nonummy. Varius praesent dictumst";

	@Test
	void contextLoads() {
	}

	@Test
    public void testEncryptViaRSA() {
        try {
            String encrypted = encryptionUtil.encryptViaRSA("status");
			System.out.println(encrypted);
			String decryption = encryptionUtil.decryptViaRSA(encrypted);
			System.out.println(decryption);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEncryptViaAES() {
        try {
            String encrypted = encryptionUtil.encryptViaAES(testText);
            System.out.println(encrypted);
            String decryption = encryptionUtil.decryptViaAES(encrypted);
            System.out.println(decryption);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Use to Generate keys for the first time to use in encryption and decryption
//    @Test
//    public void generateKeys() {
//        encryptionUtil.generateKeys();
//    }
//
//    @Test
//    public void generateSecretKey() {
//        encryptionUtil.generateAESSecret();
//    }

//    @Test
//    public void testPrivateKeyBase64() {
//        System.out.println(encryptionUtil.getPrivateKeyAsString());
//    }
//
//    @Test
//    public void testAesKeyBase64() {
//        System.out.println(encryptionUtil.getAESKeyAsString());
//    }
}
