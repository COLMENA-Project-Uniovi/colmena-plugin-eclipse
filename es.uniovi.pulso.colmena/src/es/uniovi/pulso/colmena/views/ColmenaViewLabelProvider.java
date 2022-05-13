package es.uniovi.pulso.colmena.views;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import es.uniovi.pulso.colmena.model.indicators.ColmenaIndicator;

public class ColmenaViewLabelProvider extends LabelProvider implements
		ITableLabelProvider, IColorProvider {
	private final String LABEL_SEPARATOR = ":";

	public String getColumnText(Object obj, int index) {
		ColmenaIndicator indicator = (ColmenaIndicator) obj;

		switch (index) {
		case 0:
			return indicator.getName() + LABEL_SEPARATOR;
		case 1:
			return indicator.getValue();
		default:
			return "";
		}
	}

	public Image getColumnImage(Object obj, int index) {
		switch (index) {
		case 0:
			return getImage(obj);
		}
		return null;
	}

	public Image getImage(Object obj) {
		return PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJS_INFO_TSK);
	}

	@Override
	public Color getBackground(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getForeground(Object arg0) {
		ColmenaIndicator indicator = (ColmenaIndicator) arg0;

		switch (indicator.getType()) {
		case USER_LEVEL_INITIAL:
			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		case USER_LEVEL_BASIC:
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
		case USER_LEVEL_MEDIUM:
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_YELLOW);
		case USER_LEVEL_ADVANCED:
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		case USER_LEVEL_EXPERT:
			return Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
		case USER_AVERAGE_MORE:
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
		case USER_AVERAGE_LESS:
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		case USER_AVERAGE_EQUAL:
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);
		default:
			break;
		}

		return null;
	}
}
