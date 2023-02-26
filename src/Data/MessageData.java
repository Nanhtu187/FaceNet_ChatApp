package data;

import org.apache.mina.core.session.IoSession;

/**
 * @author Tu Nguyen
 * @date 9:33 AM 2/22/2023
 */
public class MessageData {
    private String message;
    private long from;
    private long room = 0;
    private String text;
    private String type;

    public MessageData(IoSession session, String text, String type, long room) {
        this.type = type;
        this.room = room;
        from = session.getId();
        this.text = text;
        message = "<message><type>" + type + "</type><from>" + session.getId() + "</from><to>" +
                this.room + "</to><text>" + text + "</text></message>";
    }

    public MessageData(long from, String s, String type, long room){
        this.room = room;
        text = s;
        message = "<message><type>" + type + "</type><from>" + from + "</from><to>" +
                this.room + "</to><text>" + text + "</text></message>";
    }

    private String getValue(String data, String attribute) {
        return data.substring(data.indexOf("<" + attribute + ">") + attribute.length() + 2,
                                data.indexOf("</" + attribute + ">"));
    }

    private long toLong(String text) {
        long res = 0;
        for(char c: text.toCharArray()) {
            res = res * 10 + c - '0';
        }
        return res;
    }


    public MessageData(String data) {
        text = getValue(data, "text");
        type = getValue(data, "type");
        String id = getValue(data, "from");
        from = toLong(id);
        room = toLong(getValue(data,"to"));
        message = "<message><type>" + type + "</type><from>" + from + "</from><text>" + text + "</text></message>";
    }

    @Override
    public String toString() {
        return "****************************************************************\n"+
                "from: " + from + '\n' + "to:" + room + '\n' + "type: " + type + '\n' + "text: " + text+
                "\n****************************************************************\n";
    }

    public long getRoom() {
        return this.room;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return this.message;
    }

    public String getText() {
        return this.text;
    }

    public long getFrom() {
        return this.from;
    }
}
