package com.demo.springboot.Model;

public class Pole {
    private Integer id;
    private Integer wsp_x;
    private Integer wsp_y;
    private Integer stan;

    public Pole() {
    }

    public Pole(Integer id, Integer stan) {
        this.id = id;
        this.stan = stan;

        if(id > 0 && id <10) {
            setWsp_x(getId());
            setWsp_y(1);
        }
        else{
            if(id.toString().charAt(1) != '0'){
                setWsp_x(Integer.parseInt(id.toString().substring(1, 2)));
                setWsp_y(Integer.parseInt(id.toString().substring(0, 1)) + 1);
            }
            else{
                setWsp_x(10);
                if(id != 100) setWsp_y(Integer.parseInt(id.toString().substring(0, 1)));
                else setWsp_y(10);
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public Integer getWsp_x() {
        return wsp_x;
    }

    public Integer getWsp_y() {
        return wsp_y;
    }

    public Integer getStan() {
        return stan;
    }

    private void setWsp_x(Integer wsp_x) {
        this.wsp_x = wsp_x;
    }

    private void setWsp_y(Integer wsp_y) {
        this.wsp_y = wsp_y;
    }

    public void setStan(Integer stan) {
        this.stan = stan;
    }

    @Override
    public String toString() {
        return "Pole{" +
                "id='" + id + '\'' +
                ", wsp_x='" + wsp_x + '\'' +
                ", wsp_y='" + wsp_y + '\'' +
                ", stan='" + stan + '\'' +
                '}';
    }
}
