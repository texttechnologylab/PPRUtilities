import org.junit.jupiter.api.Test;
import org.texttechnologylab.data.Comment;
import org.texttechnologylab.data.ParliamentFactory;
import org.texttechnologylab.data.Speaker;
import org.texttechnologylab.data.Speech;
import org.texttechnologylab.data.impl.file.ParliamentFactory_Impl;

import java.io.IOException;
import java.util.List;

public class ConnectionTest {

    @Test
    public void test() throws IOException {

        ParliamentFactory parliamentFactory = new ParliamentFactory_Impl();

        parliamentFactory.getFractions().forEach(f->{
            System.out.println(f.getName());
            f.getMembers().stream().forEach(m->{
                System.out.println(m);
            });
        });



        Speech pTest = parliamentFactory.getComments().getFirst().getSpeech();
        System.out.println(pTest.getID());
        System.out.println(pTest.getSpeaker());
        System.out.println(pTest.getLength());
        System.out.println(pTest.getTexts());

        parliamentFactory.getSpeeches(pTest.getSpeaker()).stream().forEach(s->{
            System.out.println(s.getAgendaItem());
            System.out.println(s.getProtocol());
        });

        parliamentFactory.getSpeakers().stream().limit(5).forEach(s->{
            System.out.println(s);
        });

        parliamentFactory.getComments(pTest.getSpeaker());

        Comment pComment = parliamentFactory.getComments().getFirst();
        System.out.println(pComment.getSpeaker());
        System.out.println(pComment.getSpeech());
        System.out.println(pComment.getID());

        pTest.getTexts().stream().forEach(t->{
            if(t instanceof Comment)
                System.out.println("\t\tC: "+t.getContent());
            else
                System.out.println(t.getContent());

        });

//        List<Comment> comments = parliamentFactory.getComments().stream().filter(s ->
//                s.getSpeech().getID().equals(parliamentFactory.getComments().getFirst().getSpeech().getID())).toList();
//        System.out.println(comments);

        parliamentFactory.getProtocol(106).getAgendaItems().stream().forEach(ai->{
            System.out.println(ai.getIndex());
            ai.getSpeeches().stream().forEach(spee->{
                System.out.println(spee.getSpeaker());
                System.out.println(spee.getComments().size());
                System.out.println(spee.getTexts().size());

                System.out.println(spee.getText().substring(0, 100));
            });
        });

        List<Speech> pSpeechs = parliamentFactory.fullTextSearch("Europa");
        for (Speaker speaker : parliamentFactory.getProtocol(106).getSpeakers()) {
            System.out.println(speaker);
        }
        System.out.println(pSpeechs.size());

        System.out.println(parliamentFactory.getSpeakers().size());

        for (Speaker speaker : parliamentFactory.getSpeakers()) {
            System.out.println(speaker);
            System.out.println(speaker.getSpeeches().size());
        }
        System.out.println(parliamentFactory.getFractions().size());
        System.out.println(parliamentFactory.getComments().size());

    }

}
