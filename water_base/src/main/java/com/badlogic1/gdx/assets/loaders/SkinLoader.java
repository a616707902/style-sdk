/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic1.gdx.assets.loaders;

import com.badlogic1.gdx.assets.AssetDescriptor;
import com.badlogic1.gdx.assets.AssetLoaderParameters;
import com.badlogic1.gdx.assets.AssetManager;
import com.badlogic1.gdx.files.FileHandle;
import com.badlogic1.gdx.graphics.Texture;
import com.badlogic1.gdx.graphics.g2d.BitmapFont;
import com.badlogic1.gdx.graphics.g2d.TextureAtlas;
import com.badlogic1.gdx.scenes.scene2d.ui.Skin;
import com.badlogic1.gdx.utils.Array;

/** {@link AssetLoader} for {@link Skin} instances. All {@link Texture} and {@link BitmapFont} instances will be loaded as
 * dependencies. Passing a {@link SkinParameter} allows one to specify the exact name of the texture associated with the skin.
 * Otherwise the skin texture is looked up just as with a call to {@link Skin#Skin(FileHandle)}.
 * @author Nathan Sweet */
public class SkinLoader extends AsynchronousAssetLoader<Skin, SkinLoader.SkinParameter> {
	public SkinLoader (FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public Array<AssetDescriptor> getDependencies (String fileName, SkinParameter parameter) {
		Array<AssetDescriptor> deps = new Array();
		if (parameter == null)
			deps.add(new AssetDescriptor(resolve(fileName).pathWithoutExtension() + ".atlas", TextureAtlas.class));
		else
			deps.add(new AssetDescriptor(parameter.textureAtlasPath, TextureAtlas.class));
		return deps;
	}

	@Override
	public void loadAsync (AssetManager manager, String fileName, SkinParameter parameter) {
	}

	@Override
	public Skin loadSync (AssetManager manager, String fileName, SkinParameter parameter) {
		String textureAtlasPath;
		if (parameter == null)
			textureAtlasPath = resolve(fileName).pathWithoutExtension() + ".atlas";
		else
			textureAtlasPath = parameter.textureAtlasPath;
		TextureAtlas atlas = manager.get(textureAtlasPath, TextureAtlas.class);
		return new Skin(resolve(fileName), atlas);
	}

	static public class SkinParameter extends AssetLoaderParameters<Skin> {
		public final String textureAtlasPath;

		public SkinParameter (String textureAtlasPath) {
			this.textureAtlasPath = textureAtlasPath;
		}
	}
}
