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
    public void venderProduto(int id) {
    conn = new ConectaDAO().connectDB();

    try {
        // Verifica se o produto existe
        String verificaSql = "SELECT status FROM produtos WHERE id = ?";
        prep = conn.prepareStatement(verificaSql);
        prep.setInt(1, id);
        resultset = prep.executeQuery();

        if (!resultset.next()) {
            JOptionPane.showMessageDialog(null, "Produto com ID " + id + " não encontrado.");
            return;
        }

        String statusAtual = resultset.getString("status");

        if ("Vendido".equalsIgnoreCase(statusAtual)) {
            JOptionPane.showMessageDialog(null, "Este produto já foi vendido.");
            return;
        }

        // Atualiza o status para "Vendido"
        String updateSql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        prep = conn.prepareStatement(updateSql);
        prep.setInt(1, id);
        prep.executeUpdate();

        JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");

    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + erro.getMessage());
    }
}


    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
   
    ArrayList<ProdutosDTO> vendidos = new ArrayList<>();
    conn = new ConectaDAO().connectDB();

    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

    try {
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));

            vendidos.add(produto);
        }

    } catch (SQLException erro) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar produtos vendidos: " + erro.getMessage());
    }

    return vendidos;
}


}


