package latmod.lib.config;

import java.io.File;

import latmod.lib.*;
import latmod.lib.util.IDObject;

public final class ConfigFile extends IDObject implements Cloneable
{
	public final File file;
	public final ConfigList configList;
	public final boolean canEdit;
	public ConfigFileLoader loader;
	
	public ConfigFile(String id, File f, boolean edit)
	{
		super(id);
		configList = new ConfigList();
		configList.setID(id);
		configList.groups = new FastList<ConfigGroup>();
		configList.parentFile = this;
		file = LMFileUtils.newFile(f);
		canEdit = edit;
		loader = new ConfigFileLoader();
	}
	
	public void add(ConfigGroup g)
	{ configList.add(g); }
	
	public void load()
	{ loader.load(this); }
	
	public void save()
	{ loader.save(this); }
	
	public String toJsonString(boolean pretty)
	{
		configList.sort();
		String s = LMJsonUtils.toJson(LMJsonUtils.getGson(pretty), configList);
		return s;
	}
	
	public ConfigFile clone()
	{
		ConfigFile f = new ConfigFile(toString(), file, canEdit);
		f.configList.groups = configList.groups.clone();
		return f;
	}
}