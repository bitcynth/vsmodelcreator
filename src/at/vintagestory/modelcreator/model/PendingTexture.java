package at.vintagestory.modelcreator.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import at.vintagestory.modelcreator.gui.texturedialog.TextureDialog;
import at.vintagestory.modelcreator.interfaces.ITextureCallback;

public class PendingTexture
{
	public File texture;
	public ITextureCallback callback;
	
	public TextureEntry entry;

	public PendingTexture(File texture)
	{
		this(texture, (ITextureCallback) null);
	}

	public PendingTexture(File texture, File meta)
	{
		this(texture, meta, null);
	}

	public PendingTexture(File texture, ITextureCallback callback)
	{
		this.texture = texture;
		this.callback = callback;
	}

	public PendingTexture(File texture, File meta, ITextureCallback callback)
	{
		this.texture = texture;
		this.callback = callback;
	}

	public PendingTexture(TextureEntry entry)
	{
		this.entry = entry;
	}

	public void load()
	{
		try
		{
			if (entry != null) {
				TextureDialog.reloadExternalTexture(entry);
				return;
			}
			
			String errormessge = null;
			boolean isnew = false;
			
			String fileName = this.texture.getName().replace(".png", "").replaceAll("\\d*$", "");
			Texture texture = TextureDialog.getTexture(fileName);
			
			if (texture == null)
			{
				FileInputStream is = new FileInputStream(this.texture);
				texture = TextureLoader.getTexture("PNG", is);
				errormessge = TextureDialog.loadExternalTexture(this.texture);
				is.close();
				isnew = true;
			}
			
			if (callback != null) {
				callback.callback(isnew, errormessge, fileName);
			}
				
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
