import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.X509v3CertificateBuilder;
import java.math.BigInteger;
import java.util.Date;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;

public class GenerateKeystore {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        X500Principal issuer = new X500Principal("CN=localhost, OU=Developer, O=Example, L=Beijing, ST=Beijing, C=CN");
        X500Principal subject = issuer;
        BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
        Date notBefore = new Date();
        Date notAfter = new Date(notBefore.getTime() + 365L * 24 * 60 * 60 * 1000);

        X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
            issuer,
            serial,
            notBefore,
            notAfter,
            subject,
            publicKey.getEncoded()
        );

        Certificate certificate = certBuilder.build(null);

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);
        keyStore.setKeyEntry("tomcat", privateKey, "password".toCharArray(), new Certificate[]{certificate});

        try (FileOutputStream fos = new FileOutputStream("src/main/resources/keystore.p12")) {
            keyStore.store(fos, "password".toCharArray());
        }

        System.out.println("Keystore generated successfully!");
    }
}