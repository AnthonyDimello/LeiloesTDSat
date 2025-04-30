import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    public void cadastrarProduto(ProdutosDTO produto) {
    conn = new ConectaDAO().connectDB();

    String verificarSQL = "SELECT COUNT(*) FROM produtos WHERE nome = ?";
    String insertSQL = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

    try {
        PreparedStatement verificarPrep = conn.prepareStatement(verificarSQL);
        verificarPrep.setString(1, produto.getNome());
        ResultSet rs = verificarPrep.executeQuery();
        rs.next();

        if (rs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(null, "Produto já cadastrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Cancela o cadastro
        }

        prep = conn.prepareStatement(insertSQL);
        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor());
        prep.setString(3, produto.getStatus());

        prep.executeUpdate();

        // Mensagem de sucesso só aqui
        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + erro.getMessage());
    }
}
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        conn = new ConectaDAO().connectDB();

        String sql = "SELECT * FROM produtos";

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());
        }

        return listagem;
    }
}


