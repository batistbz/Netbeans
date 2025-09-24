public class ConexaoBD {
    public static Conection getConnection() {
        connection conexao = null;
        try{
            String url = "jdbc:mysql://localhost:3306/GerenciadordeClientes";
            String usuario = "";
            String senha = "";
            
         conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        }
        return conexao;
    }
}