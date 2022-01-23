function copyList(originList, newList, options, parentId) {
  for (const item of originList) {
    const newItem = { ...item, parentId };
    newItem.id = ++options.idGenerator;
    newList.push(newItem);
    if (item.children != null) {
      newItem.children = [];
      copyList(item.children, newItem.children, options, newItem.id);
    }
  }
}

function delById(req, list) {
  for (let i = 0; i < list.length; i++) {
    const item = list[i];
    console.log('remove i', i, req, req.params.id, item.id);
    if (item.id === parseInt(req.params.id)) {
      console.log('remove i', i);
      list.splice(i, 1);
      break;
    }
    if (item.children != null && item.children.length > 0) {
      delById(req, item.children);
    }
  }
}

function findById(id, list) {
  for (const item of list) {
    if (item.id === id) {
      return item;
    }
    if (item.children != null && item.children.length > 0) {
      const sub = findById(id, item.children);
      if (sub != null) {
        return sub;
      }
    }
  }
}
export default {
  findById,
  buildMock(options) {
    const name = options.name;
    if (options.copyTimes == null) {
      options.copyTimes = 29;
    }
    const list = [];
    for (let i = 0; i < options.copyTimes; i++) {
      copyList(options.list, list, options);
    }
    options.list = list;
    return [
      {
        path: '/mock/' + name + '/page',
        method: 'get',
        handle(req) {
          let data = [...list];
          let limit = 20;
          let offset = 0;
          for (const item of list) {
            if (item.children != null && item.children.length === 0) {
              item.hasChildren = false;
              item.lazy = false;
            }
          }
          let orderProp, orderAsc;
          if (req && req.body) {
            const { page, query, sort } = req.body;
            if (page.limit != null) {
              limit = parseInt(page.limit);
            }
            if (page.offset != null) {
              offset = parseInt(page.offset);
            }
            orderProp = sort.prop;
            orderAsc = sort.asc;

            if (Object.keys(query).length > 0) {
              data = list.filter((item) => {
                let allFound = true; // 是否所有条件都符合
                for (const key in query) {
                  // 判定某一个条件
                  const value = query[key];
                  if (value == null || value === '') {
                    continue;
                  }
                  if (value instanceof Array) {
                    // 如果条件中的value是数组的话，只要查到一个就行
                    if (value.length === 0) {
                      continue;
                    }
                    let found = false;
                    for (const i of value) {
                      if (item[key] instanceof Array) {
                        for (const j of item[key]) {
                          if (i === j) {
                            found = true;
                            break;
                          }
                        }
                        if (found) {
                          break;
                        }
                      } else if (
                        item[key] === i ||
                        (typeof item[key] === 'string' && item[key].indexOf(i + '') >= 0)
                      ) {
                        found = true;
                        break;
                      }
                      if (found) {
                        break;
                      }
                    }
                    if (!found) {
                      allFound = false;
                    }
                  } else if (value instanceof Object) {
                    for (const key2 in value) {
                      const v = value[key2];
                      if (v && item[key] && v !== item[key][key2]) {
                        return false;
                      }
                    }
                  } else if (item[key] !== value) {
                    allFound = false;
                  }
                }
                return allFound;
              });
            }
          }

          const start = offset;
          let end = offset + limit;
          if (data.length < end) {
            end = data.length;
          }

          if (orderProp) {
            // 排序
            data.sort((a, b) => {
              let ret = 0;
              if (a[orderProp] > b[orderProp]) {
                ret = 1;
              } else {
                ret = -1;
              }
              return orderAsc ? ret : -ret;
            });
          }

          const records = data.slice(start, end);
          const lastOffset = data.length - (data.length % limit);
          if (offset > lastOffset) {
            offset = lastOffset;
          }
          console.log('----------1111-----------')
          return {
            code: 0,
            message: 'success',
            data: {
              data: records,
              total: data.length,
              pageNo: 1,
              pageSize: 10,
            },
          };
        },
      },
      {
        path: '/mock/' + name + '/get',
        method: 'get',
        handle(req) {
          let id = req.params.id;
          id = parseInt(id);
          let current = null;
          for (const item of list) {
            if (item.id === id) {
              current = item;
              break;
            }
          }
          return {
            code: 0,
            message: 'success',
            data: current,
          };
        },
      },
      {
        path: '/mock/' + name + '/add',
        method: 'post',
        handle(req) {
          req.body.id = ++options.idGenerator;
          list.unshift(req.body);
          return {
            code: 0,
            message: 'success',
            data: req.body.id,
          };
        },
      },
      {
        path: '/mock/' + name + '/update',
        method: 'post',
        handle(req) {
          const item = findById(req.body.id, list);
          if (item) {
            Object.assign(item, req.body);
          }
          return {
            code: 0,
            message: 'success',
            data: null,
          };
        },
      },
      {
        path: '/mock/' + name + '/delete',
        method: 'post',
        handle(req) {
          delById(req, list);
          return {
            code: 0,
            message: 'success',
            data: null,
          };
        },
      },
      {
        path: '/mock/' + name + '/batchDelete',
        method: 'post',
        handle(req) {
          const ids = req.body.ids;
          for (let i = list.length - 1; i >= 0; i--) {
            const item = list[i];
            if (ids.indexOf(item.id) >= 0) {
              list.splice(i, 1);
            }
          }
          return {
            code: 0,
            message: 'success',
            data: null,
          };
        },
      },
      {
        path: "/mock/" + name + "/all",
        method: "post",
        handle(req) {
          return {
            code: 0,
            msg: "success",
            data: list
          };
        }
      }
    ];
  },
};
