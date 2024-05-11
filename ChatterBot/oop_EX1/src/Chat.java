import java.util.Scanner;

class Chat {
    public static void main(String[] args) {

        String nameBot1 = "bot1";
        String[] repliesToIllegalRequestBot1 = new String[] {"what ", "say i should say ",
                                                            "say what? <request>? what’s <request>?"
};
        String[] repliesToLegalRequestBot1 = {"You want me to say <phrase>, do you? alright: <phrase> ",
                                                "say <phrase>? okay: <phrase> "};

        String nameBot2 = "bot2";
        String[] repliesToIllegalRequestBot2 = new String[] {"whaaat ", "say say "};
        String[] repliesToLegalRequestBot2 = {"You want me to say <phrase> ok master, <phrase> ",
                "<phrase>? okay: <phrase> "};

        ChatterBot bot1 = new ChatterBot(nameBot1, repliesToIllegalRequestBot1, repliesToLegalRequestBot1);
        ChatterBot bot2 = new ChatterBot(nameBot2, repliesToIllegalRequestBot2, repliesToLegalRequestBot2);
        ChatterBot[] bots = {bot1,bot2};


        Scanner scanner = new Scanner(System.in);
        String statement = scanner.nextLine();

        for(int i = 0; true ;i++){
            statement = bots[i% bots.length].replyTo(statement);
            System.out.print(bots[i% bots.length].getName() + ": " + statement);
            scanner.nextLine(); // press enter to advance chat
        }
    }
}