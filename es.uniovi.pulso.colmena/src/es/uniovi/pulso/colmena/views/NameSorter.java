package es.uniovi.pulso.colmena.views;

import java.util.Comparator;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

class NameSorter extends ViewerSorter {

	private class SortInfo {
		@SuppressWarnings("unused")
		int columnIndex;
		Comparator<Object> comparator;
		boolean descending;
	}

	private TableViewer viewer;
	private SortInfo[] infos;

	public NameSorter(TableViewer viewer, TableColumn[] columns,
			Comparator<Object>[] comparators) {
		this.viewer = viewer;
		infos = new SortInfo[columns.length];
		for (int i = 0; i < columns.length; i++) {
			infos[i] = new SortInfo();
			infos[i].columnIndex = i;
			infos[i].comparator = comparators[i];
			infos[i].descending = false;
			createSelectionListener(columns[i], infos[i]);
		}
	}

	public int compare(Viewer viewer, Object element1, Object element2) {
		for (int i = 0; i < infos.length; i++) {
			int result = infos[i].comparator.compare(element1, element2);
			if (result != 0) {
				if (infos[i].descending)
					return -result;
				return result;
			}
		}
		return 0;
	}

	private void createSelectionListener(final TableColumn column,
			final SortInfo info) {
		column.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				sortUsing(info);
			}
		});
	}

	protected void sortUsing(SortInfo info) {
		if (info == infos[0])
			info.descending = !info.descending;
		else {
			for (int i = 0; i < infos.length; i++) {
				if (info == infos[i]) {
					System.arraycopy(infos, 0, infos, 1, i);
					infos[0] = info;
					info.descending = false;
					break;
				}
			}
		}
		viewer.refresh();
	}
}
