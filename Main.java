import java.util.List;

public class Main {
    public static void main(String[] args) {
        ValidationService service = new ValidationService();

        // --- Cenário 1: Sucesso Total ---
        NFe nfeSucesso = new NFe("123", "<xml>...</xml>", "VALIDO", 50.0);
        List<String> erros1 = service.validate(nfeSucesso);
        System.out.println("Resultado NFe 123: " + (erros1.isEmpty() ? "APROVADA" : "REJEITADA. Erros: " + erros1));

        // --- Cenário 2: Falha no XML (Condicional/Skip) ---
        NFe nfeFalhaXML = new NFe("456", "xml_invalido", "VALIDO", 50.0);
        List<String> erros2 = service.validate(nfeFalhaXML);
        System.out.println("Resultado NFe 456: " + (erros2.isEmpty() ? "APROVADA" : "REJEITADA. Erros: " + erros2));
        
        // --- Cenário 3: Falha na SEFAZ (com Rollback do DB) ---
        NFe nfeFalhaSefaz = new NFe("REJEITADA_SEFAZ", "<xml>...</xml>", "VALIDO", 50.0);
        List<String> erros3 = service.validate(nfeFalhaSefaz);
        System.out.println("Resultado NFe REJEITADA_SEFAZ: " + (erros3.isEmpty() ? "APROVADA" : "REJEITADA. Erros: " + erros3));

        // --- Cenário 4: Circuit Breaker ---
        NFe nfeCircuitBreaker = new NFe("789", "xml_invalido", "EXPIRADO", 5.0); // 3 falhas
        List<String> erros4 = service.validate(nfeCircuitBreaker);
        System.out.println("Resultado NFe 789: " + (erros4.isEmpty() ? "APROVADA" : "REJEITADA. Erros: " + erros4));
    }
}