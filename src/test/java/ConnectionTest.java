import org.junit.jupiter.api.Test;
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
