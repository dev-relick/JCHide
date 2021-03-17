package jchide.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

import jchide.Activator;
import jchide.preferences.JCHidePreferenceConstants;

@SuppressWarnings("restriction")
public class JCHideHandler extends AbstractHandler {

	private static final String BACKGROUND_COLOR_KEY = "org.eclipse.ui.editors.backgroundColor";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		IPreferenceStore javaStore = JavaPlugin.getDefault().getPreferenceStore();
		boolean storedJavaColor = store.getBoolean(JCHidePreferenceConstants.STORED_JAVA_COLOR);
		if (storedJavaColor) {
			RGB singleComment = getStoredColor(store, JCHidePreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_COLOR);
			RGB multiComment = getStoredColor(store, JCHidePreferenceConstants.EDITOR_MULTI_LINE_COMMENT_COLOR);
			RGB taskTag = getStoredColor(store, JCHidePreferenceConstants.EDITOR_TASK_TAG_COLOR);
			RGB docDefault = getStoredColor(store, JCHidePreferenceConstants.EDITOR_JAVADOC_DEFAULT_COLOR);
			RGB docTag = getStoredColor(store, JCHidePreferenceConstants.EDITOR_JAVADOC_TAG_COLOR);
			RGB docKeyword = getStoredColor(store, JCHidePreferenceConstants.EDITOR_JAVADOC_KEYWORD_COLOR);
			storeColor(javaStore, PreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_COLOR, singleComment, true);
			storeColor(javaStore, PreferenceConstants.EDITOR_MULTI_LINE_COMMENT_COLOR, multiComment, true);
			storeColor(javaStore, PreferenceConstants.EDITOR_TASK_TAG_COLOR, taskTag, true);
			storeColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_DEFAULT_COLOR, docDefault, true);
			storeColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_TAG_COLOR, docTag, true);
			storeColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_KEYWORD_COLOR, docKeyword, true);
			store.setValue(JCHidePreferenceConstants.STORED_JAVA_COLOR, false);
		} else {
			RGB javaSingleComment = getStoredColor(javaStore, PreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_COLOR);
			RGB javaMultiComment = getStoredColor(javaStore, PreferenceConstants.EDITOR_MULTI_LINE_COMMENT_COLOR);
			RGB javaTaskTag = getStoredColor(javaStore, PreferenceConstants.EDITOR_TASK_TAG_COLOR);
			RGB javaDocDefault = getStoredColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_DEFAULT_COLOR);
			RGB javaDocTag = getStoredColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_TAG_COLOR);
			RGB javaDocKeyword = getStoredColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_KEYWORD_COLOR);
			storeColor(store, JCHidePreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_COLOR, javaSingleComment, false);
			storeColor(store, JCHidePreferenceConstants.EDITOR_MULTI_LINE_COMMENT_COLOR, javaMultiComment, false);
			storeColor(store, JCHidePreferenceConstants.EDITOR_TASK_TAG_COLOR, javaTaskTag, false);
			storeColor(store, JCHidePreferenceConstants.EDITOR_JAVADOC_DEFAULT_COLOR, javaDocDefault, false);
			storeColor(store, JCHidePreferenceConstants.EDITOR_JAVADOC_TAG_COLOR, javaDocTag, false);
			storeColor(store, JCHidePreferenceConstants.EDITOR_JAVADOC_KEYWORD_COLOR, javaDocKeyword, false);
			
			store.setValue(JCHidePreferenceConstants.STORED_JAVA_COLOR, true);
			
			RGB backgroundColor = JFaceResources.getColorRegistry().getRGB(BACKGROUND_COLOR_KEY);
			storeColor(javaStore, PreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_COLOR, backgroundColor, true);
			storeColor(javaStore, PreferenceConstants.EDITOR_MULTI_LINE_COMMENT_COLOR, backgroundColor, true);
			storeColor(javaStore, PreferenceConstants.EDITOR_TASK_TAG_COLOR, backgroundColor, true);
			boolean hideJavaDoc = store.getBoolean(JCHidePreferenceConstants.HIDE_JAVA_DOC);
			if (hideJavaDoc) {
				storeColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_DEFAULT_COLOR, backgroundColor, true);
				storeColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_TAG_COLOR, backgroundColor, true);
				storeColor(javaStore, PreferenceConstants.EDITOR_JAVADOC_KEYWORD_COLOR, backgroundColor, true);
			}
		}

		return null;
	}

	private static RGB getStoredColor(IPreferenceStore store, String key) {
		RGB rgb = null;
		String rgbString = store.getString(key);
		if (rgbString != null && !rgbString.equals("")) {
			rgb = StringConverter.asRGB(store.getString(key));
		}
		return rgb;
	}

	private static void storeColor(IPreferenceStore store, String key, RGB rgb, boolean fire) {
		store.setValue(key, StringConverter.asString(rgb));
		if (fire) {
			store.firePropertyChangeEvent(key, null, rgb);
		}
	}
}
