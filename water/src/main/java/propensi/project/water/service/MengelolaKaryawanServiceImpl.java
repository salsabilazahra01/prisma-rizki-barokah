package propensi.project.water.service;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void addKaryawan(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(password);
//        return hashedPassword;
        return password;
    }

    @Override
    public boolean uniqueValueConstraint(UserModel user) {
        Optional<UserModel> uniqueUsername =  Optional.ofNullable(userDb.findByUsername(user.getUsername()));
        Optional<UserModel> uniqueEmail = userDb.findByEmailHp(user.getEmailHp());
        if (uniqueUsername.isPresent() || uniqueEmail.isPresent()) {
            return true;
        } return false;
    }

    @Override
    public boolean uniqueValueConstraintUpdate(UserModel user) {
        String currentEmail = userDb.findByUsername(user.getUsername()).getEmailHp();
        Optional<UserModel> uniqueEmail = userDb.findByEmailHp(user.getEmailHp());


        if (uniqueEmail.isPresent() && (!uniqueEmail.equals(currentEmail))){
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
        return userDb.findByUsername(username);
    }

    @Override
    public void updateUser(UserModel user) {
        UserModel updatedUser = retrieveUserDetail(user.getUsername());
        updatedUser.setNama(user.getNama());
        updatedUser.setEmailHp(user.getEmailHp());
        updatedUser.setRole(user.getRole());
    }

    @Override
    public void deleteUser(UserModel user) {
        userDb.delete(user);
    }
}
