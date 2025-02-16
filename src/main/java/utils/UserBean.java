package utils;

public class UserBean {
    private String id;
    private String name;
    private String password; // ハッシュ化されたパスワード
    private String salt; // ソルト

    // コンストラクタ
    public UserBean(String id, String name, String password, String salt) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    // ゲッターとセッター
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
