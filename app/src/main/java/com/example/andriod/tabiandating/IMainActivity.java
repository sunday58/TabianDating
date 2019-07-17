package com.example.andriod.tabiandating;

import com.example.andriod.tabiandating.models.Message;
import com.example.andriod.tabiandating.models.User;

public interface IMainActivity {

    void inflateViewProfileFragment(User user);
    void onMessageSelected(Message message);
    void onBackPressed();
}
