package at.vintagestory.modelcreator.model;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import org.newdawn.slick.opengl.Texture;

import at.vintagestory.modelcreator.ModelCreator;
import at.vintagestory.modelcreator.util.watchservice.*;

public class TextureEntry
{
	public String code;
	public ImageIcon icon;
	public Texture texture;
	public String filePath;
	
	public int Width;
	public int Height;

	DirectoryWatchService watchService;

	public TextureEntry(String code, Texture texture, ImageIcon image, String textureLocation)
	{
		this.code = code;
		this.texture = texture;
		this.icon = image;
		this.filePath = textureLocation;
		
		if (image == null) return;
		
		Width = texture.getImageWidth();
		Height = texture.getImageHeight();
		
		TextureEntry self = this;
		
		File f = new File(textureLocation);

        try {
            watchService = new SimpleDirectoryWatchService(); // May throw
            watchService.register( // May throw
                    new DirectoryWatchService.OnFileChangeListener() {
                        @Override
                        public void onFileModify(String filePath) {
                            
                        	if (ModelCreator.autoreloadTexture && ModelCreator.currentProject != null) {
                    			ModelCreator.Instance.AddPendingTexture(new PendingTexture(self));
                        	}
                        	
                        }
                    },
                    f.getParent(),
                    f.getName()
            );
            
            watchService.start();
        } catch (IOException e) {
            System.out.println("Unable to register file change listener for " + textureLocation);
        }
	}
	
	public void Dispose() {
		watchService.stop();
	}

	public String getCode()
	{
		return code;
	}

	public Texture getTexture()
	{
		return texture;
	}


	public ImageIcon getIcon()
	{
		return icon;
	}

	public String getFilePath()
	{
		return filePath;
	}
	
	
	public float VoxelWidthWithLwJglFuckery() {
		float scale = (float)Width / texture.getTextureWidth(); 
		return ModelCreator.currentProject.TextureWidth / scale;
	}
	
	public float VoxelHeighthWithLwJglFuckery() {
		float scale = (float)Height / texture.getTextureHeight();
		return ModelCreator.currentProject.TextureHeight / scale;
	}

}