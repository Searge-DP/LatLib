package latmod.lib.config;

import com.google.gson.JsonElement;
import latmod.lib.*;

import java.io.File;

public class ConfigFile extends ConfigGroup
{
	private File file;
	private String displayName;
	
	public ConfigFile(String id)
	{
		super(id);
	}
	
	public ConfigFile getConfigFile()
	{ return this; }
	
	public void setFile(File f)
	{ file = LMFileUtils.newFile(f); }
	
	public File getFile()
	{ return file; }
	
	public void setDisplayName(String s)
	{ displayName = (s == null || s.isEmpty()) ? null : s; }
	
	public String getDisplayName()
	{ return displayName == null ? LMStringUtils.firstUppercase(ID) : displayName; }
	
	public void load()
	{
		JsonElement e = LMJsonUtils.fromJson(file);
		
		if(e.isJsonObject())
		{
			ConfigGroup g = new ConfigGroup(null);
			g.setJson(e.getAsJsonObject());
			loadFromGroup(g);
		}
	}
	
	public void save()
	{ LMJsonUtils.toJson(file, getJson()); }
	
	public void writeExtended(ByteIOStream io)
	{
		super.writeExtended(io);
		io.writeUTF(displayName);
	}
	
	public void readExtended(ByteIOStream io)
	{
		super.readExtended(io);
		displayName = io.readUTF();
	}
	
	public void addGroup(String id, Class<?> c)
	{ add(new ConfigGroup(id).addAll(c, null, false), false); }
}