package com.cuatrodecopas.salud.configuracion;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Configuration
@ComponentScan(basePackages = "com.cuatrodecopas.salud.data.gateways")
@RequiredArgsConstructor
public class Configuraciones{
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${aws.cognito.pool-id}")
    public String poolId;

    @Value("${aws.cognito.app-client-id}")
    public String clienId;




    @Bean
    @Qualifier("s3Client")
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
    }

    @Bean
    public RekognitionClient rekognitionClient() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, accessSecret);
        return RekognitionClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .build();
    }
    @Bean
    public AmazonRekognition amazonRekognitionCliente() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        return AmazonRekognitionClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
/*
    @Bean
    public AWSCognitoIdentityProvider cognitoClient() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, accessSecret);
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion("us-east-1")
                .build();
    }*/

    @Bean
    public CognitoIdentityProviderClient cognitoIdentityProviderClient() {
        // Configurar las credenciales de AWS
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, accessSecret);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCredentials);

        // Crear y retornar el cliente de Cognito Identity Provider
        return CognitoIdentityProviderClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://nursenear.netlify.app","http://192.168.0.5:4200", "http://192.168.1.64:4200", "http://192.168.1.97:4200", "http://localhost:4200")
                        .allowedMethods("GET", "POST","PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("*")
                        .allowCredentials(true);
                //comentario
            }
        };
    }

    public  static String obtenerFechaActualStr() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHoraFormateada = fechaHoraActual.format(formatter);
        return fechaHoraFormateada;
    }

    public static Date obtenerFechaActual() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHoraFormateada = fechaHoraActual.format(formatter);
        Date fechaActual = toDate(fechaHoraFormateada, "dd/MM/yyyy HH:mm:ss");
        return fechaActual;
    }

    private static Date toDate(String fecha, String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        LocalDateTime localDateTime = LocalDateTime.parse(fecha, formatter);
        return java.sql.Timestamp.valueOf(localDateTime);
    }


}
