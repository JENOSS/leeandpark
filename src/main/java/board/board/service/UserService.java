package board.board.service;

import board.board.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    String selectUsername();
}
