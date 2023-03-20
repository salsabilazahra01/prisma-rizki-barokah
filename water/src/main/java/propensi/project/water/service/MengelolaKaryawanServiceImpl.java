package propensi.project.water.service;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import propensi.project.water.model.User.UserModel;
import propensi.project.water.repository.User.UserDb;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MengelolaKaryawanServiceImpl implements MengelolaKaryawanService{

    @Autowired
    private UserDb userDb;

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Override
    public void addKaryawan(UserModel user) {
        String pass = userService.encrypt(user.getPassword());
        user.setPassword(pass);
        userDb.save(user);
    }

    @Override
    public boolean uniqueValueConstraint(UserModel user) {
        Optional<UserModel> uniqueUsername =  userDb.findByUsername(user.getUsername());
        Optional<UserModel> uniqueEmail = userDb.findByEmailHp(user.getEmailHp());
        if (uniqueUsername.isPresent() || uniqueEmail.isPresent()) {
            return true;
        } return false;
    }

    @Override
    public List<UserModel> retrieveListUser(String role) {

        List<UserModel> listUser = userDb.findAll();

        // if it doesn't fulfill any of the conditions below,
        // it will retrieve all users regardless of role
        if (role==null) return listUser;

        List<UserModel> finalList = new ArrayList<>();

        // to retrieve lists of karyawan (manajer, supervisor, teknisi, pekerja)
        if (role.equals("karyawan")) {
            for (UserModel user:listUser) {
                if (user.getRole().toString().equals("MANAJER")||
                    user.getRole().toString().equals("SUPERVISOR")||
                    user.getRole().toString().equals("TEKNISI")||
                    user.getRole().toString().equals("PEKERJA")){
                    finalList.add(user);
                }
            }
        }

        // to retrieve lists of external users (partner, donatur, customer)
        else if (role.equals("eksternal")) {
            for (UserModel user:listUser) {
                if (user.getRole().toString().equals("PARTNER")||
                    user.getRole().toString().equals("DONATUR")||
                    user.getRole().toString().equals("CUSTOMER")) {
                    finalList.add(user);
                }
            }
        }

        return finalList;
    }

    @Override
    public UserModel retrieveUserDetail(String username) {
        return userDb.findByUsername(username).orElse(null);
    }

    @Override
    public void updateUser(UserModel user) {
        UserModel userLama = userDb.findByUsername(user.getUsername()).orElse(null);
        userLama.setNama(user.getNama());
        userLama.setRole(user.getRole());
        userLama.setEmailHp(user.getEmailHp());
        userDb.save(userLama);
    }

    private UserModel getByEmailHp(UserModel user) {
        return userDb.findByEmailHp(user.getEmailHp()).orElse(null);
    }

    @Override
    public boolean uniqueValueConstraintUpdate(UserModel user) {
        UserModel karyawanLama = retrieveUserDetail(user.getUsername());
        String kontakKaryawanLama = karyawanLama.getEmailHp();
        String kontakKaryawanUpdated = user.getEmailHp();
        UserModel cariKaryawan = getByEmailHp(user);

        // case kontak unchanged
        if(kontakKaryawanLama.equals(kontakKaryawanUpdated)){
            updateUser(user);
            return true;
        }
        // case kontak changed
        else{
            // new kontak is unique
            if(cariKaryawan == null){
                updateUser(user);
                return true;
            }
            // new kontak is not unique
            else{
                System.out.println("masuk di false service");
                return false;
            }
        }
    }

    @Override
    public void deleteUser(UserModel user) {
        userDb.delete(user);
    }

    @Override
    public UserModel getUserByKontak(String kontak) {
        Optional<UserModel> user =  userDb.findByEmailHp(kontak);
        return user.orElse(null);
    }
}
