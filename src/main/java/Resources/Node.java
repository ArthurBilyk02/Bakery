package Resources;

public class Node {
    Node next;
    Object data;

    public Node(Object dataInfo) {
        next = null;
        data = dataInfo;
    }


    public Object getData() {
        return data;
    }

    //@SuppressWarnings("unused")
    public void setData(Object dataInfo) {
        data = dataInfo;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node dataNext) {
        next = dataNext;
    }
}
