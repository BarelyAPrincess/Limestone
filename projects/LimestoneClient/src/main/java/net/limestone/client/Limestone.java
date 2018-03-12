/**
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * <p>
 * Limestone Copyright (C) 2018 Amelia Dewitt <theameliadewitt@gmail.com>
 * Glowstone Copyright (C) 2015-2018 The Glowstone Project.
 * Glowstone Copyright (C) 2011-2014 Tad Hardesty.
 * Lightstone Copyright (C) 2010-2011 Graham Edgecombe.
 * <p>
 * All Rights Reserved.
 */
package net.limestone.client;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.shader.Framebuffer;

import org.apache.commons.io.IOUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;

import javax.imageio.ImageIO;

import io.amelia.foundation.Kernel;
import io.amelia.support.IO;
import io.amelia.support.Sys;

public class Limestone
{
	public static final Kernel.Logger L = Kernel.getLogger( Limestone.class );

	public static final boolean FULLSCREEN = false;
	public static final int WINDOW_HEIGHT = 480;
	public static final int WINDOW_WIDTH = 854;
	public static final String WINDOW_TITLE = "Limestone 2018.0";
	private final MetadataSerializer metadataSerializer = new MetadataSerializer();
	boolean everythingIsFine;
	private int displayHeight;
	private int displayWidth;
	private Framebuffer framebuffer;

	public Limestone()
	{
		ImageIO.setUseCache( false );
		Locale.setDefault( Locale.ROOT );
	}

	public void boot() throws LWJGLException, IOException
	{
		everythingIsFine = true;

		L.info( "LWJGL Version: %s", ( Object ) org.lwjgl.Sys.getVersion() );

		// Setup Game Window

		if ( !Sys.isMac() )
		{
			InputStream is16 = Limestone.class.getResourceAsStream( "limestone-16x16.png" );
			InputStream is64 = Limestone.class.getResourceAsStream( "limestone-64x64.png" );

			if ( is16 == null || is64 == null )
				throw new IllegalStateException( "Could not find window icons." );

			Display.setIcon( new ByteBuffer[] {IO.readImageToBuffer( is16 ), IO.readImageToBuffer( is64 )} );
		}

		if ( FULLSCREEN )
		{
			Display.setFullscreen( true );
			DisplayMode displaymode = Display.getDisplayMode();
			displayWidth = Math.max( 1, displaymode.getWidth() );
			displayHeight = Math.max( 1, displaymode.getHeight() );
		}
		else
		{
			displayHeight = WINDOW_HEIGHT;
			displayWidth = WINDOW_WIDTH;
			Display.setDisplayMode( new DisplayMode( displayWidth, displayHeight ) );
		}

		Display.setResizable( true );
		Display.setTitle( WINDOW_TITLE );

		try
		{
			Display.create( new PixelFormat().withDepthBits( 24 ) );
		}
		catch ( LWJGLException lwjglexception )
		{
			L.severe( "Couldn't set pixel format", lwjglexception );

			try
			{
				Thread.sleep( 1000L );
			}
			catch ( InterruptedException var3 )
			{
				// Do Nothing
			}

			// if ( FULLSCREEN )
			//updateDisplayMode();

			Display.create();
		}

		OpenGlHelper.initializeTextures();
		framebuffer = new Framebuffer( this.displayWidth, this.displayHeight, true );
		framebuffer.setFramebufferColor( 0.0F, 0.0F, 0.0F, 0.0F );

		metadataSerializer.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
		metadataSerializer.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
		metadataSerializer.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
		metadataSerializer.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
		metadataSerializer.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);

		drawSplashScreen( framebuffer );

		enterGameLoop();
	}

	private void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException
	{
		ScaledResolution scaledresolution = new ScaledResolution(this);
		int i = scaledresolution.getScaleFactor();
		Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i, true);
		framebuffer.bindFramebuffer(false);
		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, (double)scaledresolution.getScaledWidth(), (double)scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.0F);
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		GlStateManager.disableDepth();
		GlStateManager.enableTexture2D();
		InputStream inputstream = null;

		try
		{
			inputstream = this.mcDefaultResourcePack.getInputStream(LOCATION_MOJANG_PNG);
			this.mojangLogo = textureManagerInstance.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputstream)));
			textureManagerInstance.bindTexture(this.mojangLogo);
		}
		catch (IOException ioexception)
		{
			LOGGER.error("Unable to load logo: {}", LOCATION_MOJANG_PNG, ioexception);
		}
		finally
		{
			IOUtils.closeQuietly(inputstream);
		}

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(0.0D, (double)this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
		bufferbuilder.pos((double)this.displayWidth, (double)this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
		bufferbuilder.pos((double)this.displayWidth, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
		bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
		tessellator.draw();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		int j = 256;
		int k = 256;
		this.draw((scaledresolution.getScaledWidth() - 256) / 2, (scaledresolution.getScaledHeight() - 256) / 2, 0, 0, 256, 256, 255, 255, 255, 255);
		GlStateManager.disableLighting();
		GlStateManager.disableFog();
		framebuffer.unbindFramebuffer();
		framebuffer.framebufferRender(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i);
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		this.updateDisplay();
	}

	private void enterGameLoop()
	{

	}
}
