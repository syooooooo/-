package utils;

import java.sql.Timestamp;

public class MemoBean {
    private int id;           // メモID
    private String user_id;       // ユーザーID
    private String title;
    private String text;   // メモの内容
    private Timestamp create_day;  // 作成日時
    private Timestamp update_day;  // 更新日時



    // コンストラクタ


    public MemoBean(int id, String user_id, String title, String text, Timestamp create_day, Timestamp update_day) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.text = text;
        this.create_day = create_day;
        this.update_day = update_day;
    }

    // ゲッターとセッター


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreate_day() {
        return create_day;
    }

    public void setCreate_day(Timestamp create_day) {
        this.create_day = create_day;
    }

    public Timestamp getUpdate_day() {
        return update_day;
    }

    public void setUpdate_day(Timestamp update_day) {
        this.update_day = update_day;
    }

    @Override
    public String toString() {
        return "MemoBean{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", title='" + title +
                ", text='" + text + '\'' +
                ", create_day=" + create_day +
                ", update_day=" + update_day +
                '}';
    }
}