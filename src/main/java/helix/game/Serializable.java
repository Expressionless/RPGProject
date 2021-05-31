package helix.game;

import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;

public interface Serializable {

	public boolean write(DataWriter writer, int pos);
	public boolean parse(DataReader reader, int pos);
}
