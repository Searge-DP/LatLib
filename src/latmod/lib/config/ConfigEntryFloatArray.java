package latmod.lib.config;

import com.google.gson.*;

import latmod.lib.*;

public class ConfigEntryFloatArray extends ConfigEntry
{
	private float[] value;
	
	public ConfigEntryFloatArray(String id, float[] def)
	{
		super(id, PrimitiveType.FLOAT_ARRAY);
		set(def);
	}
	
	public void set(float[] o)
	{ value = o == null ? new float[0] : o; }
	
	public float[] get()
	{ return value; }
	
	public final void setJson(JsonElement o)
	{
		JsonArray a = o.getAsJsonArray();
		value = new float[a.size()];
		for(int i = 0; i < value.length; i++)
			value[i] = a.get(i).getAsFloat();
		set(value);
	}
	
	public final JsonElement getJson()
	{
		JsonArray a = new JsonArray();
		value = get();
		for(int i = 0; i < value.length; i++)
			a.add(new JsonPrimitive(value[i]));
		return a;
	}
	
	void write(ByteIOStream io)
	{
		value = get();
		io.writeUShort(value.length);
		for(int i = 0; i < value.length; i++)
			io.writeFloat(value[i]);
	}
	
	void read(ByteIOStream io)
	{
		value = new float[io.readUShort()];
		for(int i = 0; i < value.length; i++)
			value[i] = io.readFloat();
		set(value);
	}
}