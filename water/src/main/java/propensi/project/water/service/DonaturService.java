package propensi.project.water.service;

import java.util.List;

import propensi.project.water.model.User.DonaturModel;
import propensi.project.water.model.User.UserModel;

public interface DonaturService {
    public void addDonatur(DonaturModel donatur);

    public List<DonaturModel> getListDonatur();

    public DonaturModel getDonaturByUsername(String username);

    public String encrypt(String password);

    void save(DonaturModel donaturModel);
}
