package GamePre;/*Author:l
Explain:用于规范化读取文件中的信息
Version:1.0*/

public class Node {
    int x;
    int y;
    int directory;
    int health_value;

    public Node(int x, int y, int directory, int health_value) {
        this.x = x;
        this.y = y;
        this.directory = directory;
        this.health_value = health_value;
    }
}
