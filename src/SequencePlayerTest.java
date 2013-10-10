import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import org.junit.Test;

import player.Piece;
import player.SimpleReader;
import player.abcLexer;
import player.abcParser;
import sound.Pitch;
import sound.SequencePlayer;

public class SequencePlayerTest {
    @Test
    public void piece1() {
        SequencePlayer player;
        try {
            //testing git folder
            
            // create a new player, with 140 beats (i.e. quarter note) per
            // minute, with 1 tick per quarter note
            player = new SequencePlayer(140, 12);

            player.addNote(new Pitch('C').toMidiNote(), 0, 12);
            player.addNote(new Pitch('C').toMidiNote(), 12, 12);
            player.addNote(new Pitch('C').toMidiNote(), 24, 9);
            player.addNote(new Pitch('D').toMidiNote(), 33, 3);
            player.addNote(new Pitch('E').toMidiNote(), 36, 12);

            player.addNote(new Pitch('E').toMidiNote(), 48, 9);
            player.addNote(new Pitch('D').toMidiNote(), 57, 3);
            player.addNote(new Pitch('E').toMidiNote(), 60, 9);
            player.addNote(new Pitch('F').toMidiNote(), 69, 3);
            player.addNote(new Pitch('G').toMidiNote(), 72, 24);

            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(),
                    96, 4);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(),
                    100, 4);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(),
                    104, 4);
            player.addNote(new Pitch('G').toMidiNote(), 108, 4);
            player.addNote(new Pitch('G').toMidiNote(), 112, 4);
            player.addNote(new Pitch('G').toMidiNote(), 116, 4);
            player.addNote(new Pitch('E').toMidiNote(), 120, 4);
            player.addNote(new Pitch('E').toMidiNote(), 124, 4);
            player.addNote(new Pitch('E').toMidiNote(), 128, 4);
            player.addNote(new Pitch('C').toMidiNote(), 132, 4);
            player.addNote(new Pitch('C').toMidiNote(), 136, 4);
            player.addNote(new Pitch('C').toMidiNote(), 140, 4);

            player.addNote(new Pitch('G').toMidiNote(), 144, 9);
            player.addNote(new Pitch('F').toMidiNote(), 153, 3);
            player.addNote(new Pitch('E').toMidiNote(), 156, 9);
            player.addNote(new Pitch('D').transpose(Pitch.OCTAVE).toMidiNote(), 165, 3);
            player.addNote(new Pitch('C').toMidiNote(), 168, 24);

            System.out.println(player);

            // play!
            player.play();
            /*
             * Note: A possible weird behavior of the Java sequencer: Even if
             * the sequencer has finished playing all of the scheduled notes and
             * is manually closed, the program may not terminate. This is likely
             * due to daemon threads that are spawned when the sequencer is
             * opened but keep on running even after the sequencer is killed. In
             * this case, you need to explicitly exit the program with
             * System.exit(0).
             */
            // System.exit(0);

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void piece2() {
        SequencePlayer player;
        try {

            // create a new player, with 140 beats (i.e. quarter note) per
            // minute, with 1 tick per quarter note
            player = new SequencePlayer(200, 12);

            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 0, 6);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 0, 6);
            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 6, 6);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 6, 6);
            //We have a rest here this is why we jumped from 11 to 18
            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 18, 6);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 18, 6);
            //we have a rest here this is why we jumped from 23 to 30
            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 30, 6);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 30, 6);
            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 36, 12);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 36, 12);

            player.addNote(new Pitch('G').toMidiNote(), 48, 12);
            player.addNote(new Pitch('B').toMidiNote(), 48, 12);
            player.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 48, 12);
            //We have a rest here jump from 60 to 72
            player.addNote(new Pitch('G').toMidiNote(), 72, 12);
            //We have a rest here jump from 84 to 96
            
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 96, 18);
            player.addNote(new Pitch('G').toMidiNote(), 114, 3);
            //we have a rest here jump from 116 to 128
            player.addNote(new Pitch('E').toMidiNote(), 129, 12);
            
            player.addNote(new Pitch('E').toMidiNote(), 141, 6);
            player.addNote(new Pitch('A').toMidiNote(), 147, 12);
            player.addNote(new Pitch('B').toMidiNote(), 159, 12);
            player.addNote(new Pitch('B').transpose(-1).toMidiNote(), 171, 6);
            player.addNote(new Pitch('A').toMidiNote(), 177, 12);
            
            player.addNote(new Pitch('G').toMidiNote(), 189, 8);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 197, 8);
            player.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 205, 8);
            player.addNote(new Pitch('A').transpose(Pitch.OCTAVE).toMidiNote(), 213, 12);
            player.addNote(new Pitch('F').transpose(Pitch.OCTAVE).toMidiNote(), 225, 6);
            player.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 231, 6);
            
            //We have a rest from 236 to 242
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 243, 12);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 255, 6);
            player.addNote(new Pitch('D').transpose(Pitch.OCTAVE).toMidiNote(), 261, 6);
            player.addNote(new Pitch('B').toMidiNote(), 267, 9);


            // play!
            player.play();
            
            SimpleReader sr = new SimpleReader();
            String fileForLexer = sr.FileToString("sample_abc/piece2.abc");
            abcLexer lex = new abcLexer(fileForLexer);
            abcParser parser = new abcParser(lex);
            parser.parse();
            System.out.println(parser.getDenoms());
            Piece piece = new Piece(parser.getVoice(),parser.getHeader(),
                    parser.hasDublet(),parser.hasTriplet(),parser.hasQuad(),parser.getDenoms());

            
            /*
             * Note: A possible weird behavior of the Java sequencer: Even if
             * the sequencer has finished playing all of the scheduled notes and
             * is manually closed, the program may not terminate. This is likely
             * due to daemon threads that are spawned when the sequencer is
             * opened but keep on running even after the sequencer is killed. In
             * this case, you need to explicitly exit the program with
             * System.exit(0).
             */
            // System.exit(0);

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

}
