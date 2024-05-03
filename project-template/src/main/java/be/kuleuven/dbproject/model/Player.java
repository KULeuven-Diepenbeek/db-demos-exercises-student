package be.kuleuven.dbproject.model;

import java.util.Objects;

public class Player {
    private String name;
    private int clubId;
    private int id;

    public Player(String name, int clubId, int id) {
        this.name = name;
        this.clubId = clubId;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return clubId == player.clubId && id == player.id && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, clubId, id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", clubId=" + clubId +
                ", id=" + id +
                '}';
    }
}
