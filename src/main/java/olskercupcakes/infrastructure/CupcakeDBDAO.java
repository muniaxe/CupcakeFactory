package olskercupcakes.infrastructure;

import olskercupcakes.domain.cupcake.Cupcake;
import olskercupcakes.domain.cupcake.CupcakeNoCakeFoundException;
import olskercupcakes.domain.cupcake.CupcakeNoToppingsFoundException;
import olskercupcakes.domain.cupcake.CupcakeRepository;
import olskercupcakes.domain.user.User;
import olskercupcakes.domain.user.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CupcakeDBDAO implements CupcakeRepository {

    private final Database db;

    public CupcakeDBDAO(Database db) {
        this.db = db;
    }


    private Cupcake.Topping loadTopping(ResultSet rs) throws SQLException {
        //Topping(int id, String name, int price)
        return new Cupcake.Topping(
                rs.getInt("cupcake_toppings.id"),
                rs.getString("cupcake_toppings.name"),
                rs.getInt("cupcake_toppings.price"));
    }

    @Override
    public List<Cupcake.Topping> findAllToppings() throws CupcakeNoToppingsFoundException{
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM cupcake_toppings");
            ResultSet rs = s.executeQuery();
            ArrayList<Cupcake.Topping> toppings = new ArrayList<>();
            while (rs.next()) {
                toppings.add(loadTopping(rs));
            }
            if (toppings.size() > 0){
                return toppings;
            } else {
                throw new CupcakeNoToppingsFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Cupcake.Cake loadCake(ResultSet rs) throws SQLException {
        //Cake(int id, String name, int price)
        return new Cupcake.Cake(
                rs.getInt("cupcake_toppings.id"),
                rs.getString("cupcake_toppings.name"),
                rs.getInt("cupcake_toppings.price"));
    }

    @Override
    public List<Cupcake.Cake> findAllCakes() throws CupcakeNoCakeFoundException {
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement(
                    "SELECT * FROM cupcake_cakes");
            ResultSet rs = s.executeQuery();
            ArrayList<Cupcake.Cake> cakes = new ArrayList<>();
            while (rs.next()) {
                cakes.add(loadCake(rs));
            }
            if (cakes.size() > 0){
                return cakes;
            } else {
                throw new CupcakeNoCakeFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cupcake.Cake findCake(int id) {
        return null;
    }

    @Override
    public Cupcake.Topping findTopping(int id) {
        return null;
    }
}
