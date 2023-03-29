package com.fintech.kkpayments.listeners;

import com.fintech.kkpayments.data.model.PackageType;
import com.fintech.kkpayments.data.model.PersonUserModel;
import com.fintech.kkpayments.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}
