package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig{
    @Bean
    public OpenAPI openAPI() {
        Info info = createInfo();
        info.setContact(createContact());
        info.setLicense(createLicense());
        return new OpenAPI()
                .info(info);
    }

    private Info createInfo(){
        var info = new Info();
        info.setTitle("API RH");
        info.setDescription("API de serviço para sistema de RH da SEAP");
        info.setVersion("v1");
        return info;
    }
    private Contact createContact(){
        var contato = new Contact();
        contato.setName("Supervisão de Desenvolvimento de Sistemas");
        contato.setEmail("desenvolvimento@seap.ma.gov.br");
        contato.setUrl("https://seap.ma.gov.br");
        return contato;
    }
    private License createLicense(){
        var licenca = new License();
        licenca.setName("Copyright (C) Secretaria de Estado Administração Penitenciária - Todos os direitos reservados ");
        licenca.setUrl("https://seap.ma.gov.br");
        return licenca;
    }
}