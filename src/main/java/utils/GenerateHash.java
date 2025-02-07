package utils;

import org.mindrot.jbcrypt.BCrypt;

public class GenerateHash {
  // ハッシュ生成メソッド
  static public String getHashPw(String password, String salt) {
    String hashedPassword = BCrypt.hashpw(password, salt);
    return hashedPassword;
  }

  // ソルト生成メソッド
  static public String getSalt() {
    return BCrypt.gensalt(); // ランダムな文字列作成
  }

  // パスワード検査メソッド
  static public boolean checkPassword(String planePassword, String hashedPassword) {
    return BCrypt.checkpw(planePassword, hashedPassword);
  }

}