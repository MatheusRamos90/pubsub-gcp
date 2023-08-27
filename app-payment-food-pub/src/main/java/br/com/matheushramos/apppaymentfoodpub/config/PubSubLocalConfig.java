//package br.com.matheushramos.apppaymentfoodpub.config;
//
//import com.google.auth.Credentials;
//import com.google.cloud.spring.autoconfigure.core.GcpProperties;
//import com.google.cloud.spring.core.CredentialsSupplier;
//import com.google.cloud.spring.core.DefaultCredentialsProvider;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//import java.io.IOException;
//
//@Configuration
//@Profile("local")
//public class PubSubLocalConfig {
//
//    @Bean
//    public DefaultCredentialsProvider customCredentialsProvider() throws IOException {
//        var c = new GcpProperties();
//        return new CustomCredentialsProvider(c);
//    }
//
//    private static class CustomCredentialsProvider extends DefaultCredentialsProvider {
//        public CustomCredentialsProvider(CredentialsSupplier credentialsSupplier) throws IOException {
//            super(credentialsSupplier);
//        }
//
//        @Override
//        public Credentials getCredentials() {
//            return null;
//        }
//    }
//
//}
