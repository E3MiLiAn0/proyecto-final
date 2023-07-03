package com.cuatrodecopas.salud.data.gateways.implementaciones;

import com.cuatrodecopas.salud.core.entidades.Usuario;
import com.cuatrodecopas.salud.data.gateways.interfaces.AutenticacionUsuarioGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Map;

@Component
public class CognitoAutenticacionUsuarioGatewayImpl implements AutenticacionUsuarioGateway {
    @Value("${aws.cognito.pool-id}")
    private String usuarioPoolId;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessToken;

    @Value("${aws.cognito.app-client-id}")
    private String clientId;

    @Value("${cloud.aws.credentials.secret-key}")
    private String accessSecret;

    CognitoIdentityProviderClient cognitoIdentityProviderClient;
    //AWSCognitoIdentityProvider cognitoClient;


    public CognitoAutenticacionUsuarioGatewayImpl(CognitoIdentityProviderClient cognitoIdentityProviderClient) {
        //this.cognitoClient = cognitoClient;
        this.cognitoIdentityProviderClient=cognitoIdentityProviderClient;
    }
    @Override
    public void registrarUsuario(Usuario usuario) {
        try {
            AdminCreateUserRequest createUserRequest = prepararUsuarioParaCognito(usuario);
            cognitoIdentityProviderClient.adminCreateUser(createUserRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario en Cognito", e);
        }
    }

    @Override
    public String loginUsuario(String mail, String password) {
        // Configurar los parámetros de inicio de sesión
        AdminInitiateAuthRequest autorizacionRequest = prepararLogin(mail,password);

        // Iniciar sesión y obtener la respuesta
        AdminInitiateAuthResponse autenticacionRespuesta = cognitoIdentityProviderClient.adminInitiateAuth(autorizacionRequest);
        AuthenticationResultType autenticacionResultado = autenticacionRespuesta.authenticationResult();


        // Obtener los tokens de acceso, actualización y ID de Cognito
        String accessToken = autenticacionResultado.accessToken();
        String refreshToken = autenticacionResultado.refreshToken();
        String idToken = autenticacionResultado.idToken();

        // Puedes utilizar los tokens según tus necesidades
        return "Token de acceso: " + accessToken;
    }

    public void listaDeUsuariosEnCognito(){
        try {
            ListUsersRequest usersRequest = ListUsersRequest.builder()
                    .userPoolId(usuarioPoolId)
                    .build();

            ListUsersResponse response = cognitoIdentityProviderClient.listUsers(usersRequest);
            response.users().forEach(user -> {
                System.out.println("User " + user.username() + " Status " + user.userStatus() + " Created " + user.userCreateDate() );
            });

        } catch (CognitoIdentityProviderException e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
    private AdminInitiateAuthRequest prepararLogin(String mail, String password) {

        try {
            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .authFlow("ADMIN_NO_SRP_AUTH")
                    .clientId(clientId)
                    .userPoolId(usuarioPoolId)
                    .authParameters(Map.of(
                            "USERNAME", mail,
                            "PASSWORD", password
                    ))
                    .build();
            return authRequest;
        }catch (Exception e){
            throw new RuntimeException("Error al preparar el usuario para Login", e);
        }
    }

    private AdminCreateUserRequest prepararUsuarioParaCognito(Usuario usuario) throws RuntimeException{
        try {
            AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
                    .userPoolId(usuarioPoolId)
                    .username(usuario.getMail())
                    .temporaryPassword(usuario.getPassword())
                    .userAttributes(
                            AttributeType.builder().name("email").value(usuario.getMail()).build(),
                            AttributeType.builder().name("custom:dni").value(usuario.getDni()).build(),
                            AttributeType.builder().name("custom:nombre").value(usuario.getNombre()).build(),
                            AttributeType.builder().name("custom:apellido").value(usuario.getApellido()).build(),
                            AttributeType.builder().name("email_verified").value("true").build()
                    )
                    .build();

            return createUserRequest;
        } catch (Exception e) {
            throw new RuntimeException("Error al preparar el usuario para Cognito", e);
        }
    }
}
