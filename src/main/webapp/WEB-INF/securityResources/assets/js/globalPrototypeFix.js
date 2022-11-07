'use strict';

// Overlay global
/* eslint-disable no-extra-parens */
(function(global) {

	Number.prototype.currency = function() {
		return ((parseFloat(this || 0, 10).toFixed(2) + "") || '0.00').replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}

	global.currency = (param, repair) => ((repair && (param === null || param === undefined)) ? repair : Number(param).currency()) || '0.00';

	kendo.ui.Dialog.prototype.header = function(content) {
		this.element.prev().find(".k-window-title").html(content);
	}

	kendo.ui.DatePicker.prototype.val = function() {
		return moment(this.value()).format('YYYY-MM-DD') + 'T00:00:00.000Z';
	}

	// 8px ~ 30px
	kendo.ui.Editor.defaultTools.fontSize.options.items = Array(14).fill().map((e, i) =>
		({ text: (i * 2 + 8) + 'px', value: (i * 2 + 8) + 'px' }));

	kendo.ui.editor.Toolbar.prototype.refreshTools = function() {
		var that = this,
			editor = that._editor,
			range = editor.getRange(),
			nodes = kendo.ui.editor.RangeUtils.textNodes(range);
		if (!nodes.length) {
			nodes = [range.startContainer];
		}
		that.items().each(function() {
			var tool = that.tools[that._toolName(this)];
			if (tool && tool.update) {
				if (tool.name === "fontsize" || tool.name === "fontname") {
					var list = $(this).data(tool.type);
					list.close();
					var val = tool.finder.getFormat(nodes);
					if (val === "inherit") {
						var node = nodes[0];
						node = node.nodeType === 3 ? node.parentNode : node;
						var style = tool.name === "fontname" ? "font-family" : "font-size";
						val = that._editor.window.getComputedStyle(node)[style];
					}
					list.value(val);
				} else {
					tool.update($(this), nodes);
				}
			}
		});
		this.update();
	};

})(window);
