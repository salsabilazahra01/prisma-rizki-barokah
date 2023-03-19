package propensi.project.water.service;

import org.springframework.stereotype.Service;
import propensi.project.water.model.User.UserModel;

import java.util.List;

public interface MengelolaKaryawanService {

    void addKaryawan(UserModel user);

    public String encrypt(String password);

    public boolean uniqueValueConstraint(UserModel user);

    public boolean uniqueValueConstraintUpdate(UserModel user);

    List<UserModel> retrieveListUser(String role);

    UserModel retrieveUserDetail(String username);

    void updateUser(UserModel user);

    void deleteUser(UserModel user);

}
