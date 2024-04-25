package GamePre;/*Author:l
Explain:
Version:1.0*/

public class Button {
    boolean button_State=false;//这里是想实现鼠标经过有变色效果
    int life=10;


    public boolean isButton_State() {
        return button_State;
    }

    public void setButton_State(boolean button_State) {
        this.button_State = button_State;
    }
    public void LifeDown(){
        if(button_State==true){
        life--;
        if(life<0){
            setButton_State(false);
            life=10;
        }

        }else{
            return;
        }
    }
}
