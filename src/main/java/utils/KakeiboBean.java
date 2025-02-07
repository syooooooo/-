package utils;

import java.sql.Date;

public class KakeiboBean {
    private int id;
    private String user_id;  // キャメルケースに変更
    private Date date;
    private String type;     // 収入 or 支出
    private String genre;    // ジャンル（例: 食費、家賃）
    private String title;    // タイトルまたは説明
    private int amount;      // 金額

    // すべてのプロパティを持つコンストラクタ
    public KakeiboBean(int id, String user_id, Date date, String type, String genre, String title, int amount) {
        this.id = id;
        this.user_id = user_id;
        this.date = date;
        this.type = type;
        this.genre = genre;
        this.title = title;
        this.amount = amount;
    }

    // 必要最低限のフィールド（id, date, genre, amount）を持つコンストラクタ
    public KakeiboBean(int id, Date date, String genre, int amount) {
        this.id = id;
        this.date = date;
        this.genre = genre;
        this.amount = amount;
    }

    // より汎用的な入力用のコンストラクタ（'userId' と 'title' が不要な場合）
    public KakeiboBean(String type, String genre, String title, int amount) {
        this.type = type;
        this.genre = genre;
        this.title = title;
        this.amount = amount;
    }

    // デフォルトコンストラクタ（初期値なしでインスタンス化する場合に使用）
    public KakeiboBean() {
    }

    public KakeiboBean(String user_id, Date date, String genre, String title, int amount) {
        this.user_id = user_id;
        this.date = date;
        this.genre = genre;
        this.title = title;
        this.amount = amount;
    }

    public KakeiboBean(String genre, String title, int amount, int id) {
        this.genre = genre;
        this.title = title;
        this.amount = amount;
        this.id = id;
    }

    // 各フィールドのゲッターとセッター
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
